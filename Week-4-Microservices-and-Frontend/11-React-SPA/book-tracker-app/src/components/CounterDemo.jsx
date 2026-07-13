import ClassCounter from './ClassCounter.jsx'
import FunctionCounter from './FunctionCounter.jsx'

/**
 * Wrapper that renders the class-based and hooks-based counters next to
 * each other, so both component styles are reachable from App.jsx.
 */
function CounterDemo() {
  return (
    <div className="counter-demo">
      <ClassCounter />
      <FunctionCounter />
    </div>
  )
}

export default CounterDemo
