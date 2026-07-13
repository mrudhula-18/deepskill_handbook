import { useState, useEffect } from 'react'

/**
 * BUGGY VERSION - do not copy this into a real app, it's the "before"
 * for the debugging walkthrough in ../DEBUGGING-NOTES.md.
 *
 * Bug: stale closure over `count`.
 *
 * The effect below runs exactly once (empty dependency array `[]`), so the
 * function passed to setInterval is created a single time, during the
 * render where `count` was 0. That callback closes over that specific
 * `count` variable forever - it does NOT re-read `count` on every tick.
 *
 * So every second the interval calls `setCount(count + 1)`, and `count`
 * inside the closure is always 0, so it always calls `setCount(1)`. The
 * counter goes 0 -> 1 once and then appears to freeze at 1 forever, even
 * though the interval keeps firing.
 */
function Counter() {
  const [count, setCount] = useState(0)

  useEffect(() => {
    const intervalId = setInterval(() => {
      setCount(count + 1) // BUG: `count` here is always the value from mount (0)
    }, 1000)

    return () => clearInterval(intervalId)
  }, []) // empty deps: this effect (and its closure) only runs once

  return (
    <div>
      <h3>Buggy Counter</h3>
      <p>Count: {count}</p>
      <p>Expected: increments every second. Actual: stops at 1.</p>
    </div>
  )
}

export default Counter
