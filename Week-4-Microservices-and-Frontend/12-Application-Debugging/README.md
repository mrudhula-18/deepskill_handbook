# 12 - Application Debugging

## Objectives

- Recognize a classic React bug caused by a **stale closure** in a
  `setInterval`/`useEffect` callback.
- Practice diagnosing it with **Chrome DevTools** (Sources panel
  breakpoints, Watch panel, Console).
- Practice diagnosing the same bug with the **VS Code debugger** attached
  to Chrome (breakpoints, Watch, Step Into/Over/Out, Call Stack).
- Apply the correct fix: the functional/updater form of `setState`.

## Contents

| File | Purpose |
|---|---|
| [`buggy/Counter.buggy.jsx`](buggy/Counter.buggy.jsx) | A counter that increments once (0 -> 1) and then appears to freeze, due to a stale closure over `count` inside a `setInterval` callback. |
| [`fixed/Counter.fixed.jsx`](fixed/Counter.fixed.jsx) | The same counter, fixed by using `setCount(c => c + 1)` instead of `setCount(count + 1)`. |
| [`.vscode/launch.json`](.vscode/launch.json) | A "Launch Chrome against localhost" VS Code debug configuration targeting `http://localhost:5173`, the default Vite dev port used by Module 11's `book-tracker-app`. |
| [`DEBUGGING-NOTES.md`](DEBUGGING-NOTES.md) | Step-by-step walkthrough of finding and fixing the bug with both Chrome DevTools and the VS Code debugger. |

## How to use this module

1. Read [`buggy/Counter.buggy.jsx`](buggy/Counter.buggy.jsx) and try to
   spot the bug before reading the comments.
2. Temporarily render it from Module 11's `App.jsx` (or any React app)
   and confirm the symptom in the browser: the count stops at 1.
3. Follow [`DEBUGGING-NOTES.md`](DEBUGGING-NOTES.md) to diagnose it two
   ways:
   - Chrome DevTools Sources panel + Watch panel.
   - VS Code debugger using [`.vscode/launch.json`](.vscode/launch.json)
     to launch Chrome directly from the editor.
4. Compare against [`fixed/Counter.fixed.jsx`](fixed/Counter.fixed.jsx)
   to see the one-line fix and why it works.

No build step is required for this module on its own - the two `.jsx`
files are meant to be dropped into a running React app (such as Module
11's `book-tracker-app`) to observe and debug, not built standalone.
