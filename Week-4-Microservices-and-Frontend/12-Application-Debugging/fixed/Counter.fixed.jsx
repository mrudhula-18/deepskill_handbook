import { useState, useEffect } from 'react'

/**
 * FIXED VERSION - see ../buggy/Counter.buggy.jsx for the "before" and
 * ../DEBUGGING-NOTES.md for how the bug was diagnosed.
 *
 * Fix: use the functional/updater form of setState, `setCount(c => c + 1)`,
 * instead of `setCount(count + 1)`.
 *
 * React guarantees the updater function always receives the LATEST state
 * value at the time it actually runs, regardless of which render's
 * closure created the callback. Because the interval callback no longer
 * reads the `count` variable captured at mount time, the stale-closure
 * problem disappears and a single setInterval (still created once, on
 * mount) correctly increments the counter on every tick.
 */
function Counter() {
  const [count, setCount] = useState(0)

  useEffect(() => {
    const intervalId = setInterval(() => {
      setCount((c) => c + 1) // FIX: functional updater reads the latest state, not a stale closure
    }, 1000)

    return () => clearInterval(intervalId)
  }, [])

  return (
    <div>
      <h3>Fixed Counter</h3>
      <p>Count: {count}</p>
      <p>Increments every second, correctly, forever.</p>
    </div>
  )
}

export default Counter
