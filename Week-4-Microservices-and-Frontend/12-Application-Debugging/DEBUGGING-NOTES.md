# Debugging Notes - Stale Closure in `Counter`

This module walks through diagnosing and fixing the bug in
[`buggy/Counter.buggy.jsx`](buggy/Counter.buggy.jsx), producing
[`fixed/Counter.fixed.jsx`](fixed/Counter.fixed.jsx). The symptom: a
counter that's supposed to tick up once a second instead goes from 0 to 1
and then never changes again.

## Reproducing it

Drop `buggy/Counter.buggy.jsx` into the Module 11 app (e.g. render it
temporarily from `App.jsx`), run `npm run dev`, and watch it in the
browser. You'll see "Count: 0", then one second later "Count: 1", and it
stays there.

## Method 1 - Chrome DevTools

1. Open the app in Chrome at `http://localhost:5173` and open DevTools
   (F12), **Sources** panel.
2. Find the file. Since this is a Vite dev server, source maps let you
   locate `Counter.buggy.jsx` directly under the page's source tree
   (usually under a webpack-internal/vite virtual folder, or via
   Ctrl+P / Cmd+P and typing `Counter.buggy.jsx`).
3. Set a breakpoint **inside the `setInterval` callback**, on the line
   `setCount(count + 1)`.
4. Let the app run. The breakpoint hits every second, because
   `setInterval` really is firing repeatedly - that already tells you the
   *timer* isn't the problem, so look at what's being passed to
   `setCount`.
5. Each time the breakpoint hits, open the **Scope** pane (or add
   `count` to the **Watch** panel) and check the value of `count` in the
   closure. This is the key observation: **`count` reads `0` on every
   single tick**, even after the UI has shown "Count: 1" and time has
   clearly passed. That mismatch - the watched variable never changing
   while the app's rendered output implies it should - is the signature
   of a stale closure.
6. Step **Over** the `setCount(count + 1)` call and continue: you'll see
   the app re-render with `count` = 1 in the DOM, but the *next* time the
   interval callback runs, the `count` in scope is back to `0` in the
   Watch panel. That's because this specific callback function object was
   created once, at mount, and its closure permanently captured the
   `count` binding from that first render - React re-renders with new
   state, but this old closure never sees the new value.
7. Diagnosis confirmed: replace `setCount(count + 1)` with the updater
   form `setCount(c => c + 1)`, which receives the current state from
   React itself at call time instead of relying on the closed-over
   variable. That's exactly what `fixed/Counter.fixed.jsx` does.
8. Re-run with the fix, breakpoint on the same line (now
   `setCount((c) => c + 1)`), and step into the arrow function passed to
   `setCount` - watching `c` shows it incrementing correctly on every
   tick (0, 1, 2, 3...), confirming the fix.

### Also useful in DevTools

- **React DevTools** extension: select the `Counter` component in the
  Components tab and watch its `count` hook value update in real time -
  in the buggy version it's frozen at 1; in the fixed version it climbs
  every second.
- **Console** panel: adding a `console.log('tick', count)` inside the
  interval callback (temporarily) makes the stale value visible without
  breakpoints at all - every logged line prints `tick 0` in the buggy
  version.

## Method 2 - VS Code debugger

The [`launch.json`](.vscode/launch.json) in this folder defines a
"Launch Chrome against localhost" configuration pointed at
`http://localhost:5173` (the default Vite dev server port used by Module
11's `book-tracker-app`).

1. Start the dev server first: `cd 11-React-SPA/book-tracker-app && npm run dev`.
2. Open this repo in VS Code with the Debugger for Chrome capability
   (built into modern VS Code via the JavaScript Debug Terminal, no
   extra extension needed in recent versions).
3. Copy `.vscode/launch.json` into the workspace root's `.vscode/`
   folder if it isn't already picked up from here, then open the Run and
   Debug panel (Ctrl+Shift+D) and pick **"Launch Chrome against
   localhost (Vite dev server)"**, then press F5. VS Code launches Chrome
   attached to its debugger and opens `http://localhost:5173`.
4. Open `buggy/Counter.buggy.jsx` directly in VS Code and click in the
   gutter next to `setCount(count + 1)` to set a breakpoint - VS Code's
   Chrome debugger maps it through the Vite source map just like the
   in-browser Sources panel does.
5. Use the same techniques as native DevTools, now inside the editor:
   - **Watch panel**: add the expression `count` to watch its value at
     each breakpoint hit.
   - **Step Over (F10)**: advance past `setCount(count + 1)` without
     diving into React internals.
   - **Step Into (F11)**: step into the `setCount` call itself if you
     want to see React's scheduling machinery (usually not necessary for
     this bug, but useful to know the interval callback's own local scope
     is what matters, not React's).
   - **Step Out (Shift+F11)**: pop back up out of the interval callback
     once you've confirmed the value of `count`.
   - **Call Stack panel**: confirms the breakpoint is being hit from the
     browser's timer machinery (`setInterval` callback), not from a
     render pass - reinforcing that the closure, not the render, is where
     the stale value lives.
6. Repeat against `fixed/Counter.fixed.jsx` and watch the parameter `c`
   inside the updater function - it increments correctly on every stop,
   confirming the fix without needing to touch the browser DevTools UI at
   all.

## Summary

| | Buggy | Fixed |
|---|---|---|
| Update call | `setCount(count + 1)` | `setCount((c) => c + 1)` |
| Value read | `count` from the closure captured at mount (always `0`) | `c`, the latest state, supplied by React at call time |
| Effect deps | `[]` (runs once, so the closure is never recreated) | `[]` (still runs once - now safe, since the callback no longer depends on a specific render's `count`) |
| Observed behavior | Counts 0 -> 1 and stops | Counts up every second, indefinitely |
