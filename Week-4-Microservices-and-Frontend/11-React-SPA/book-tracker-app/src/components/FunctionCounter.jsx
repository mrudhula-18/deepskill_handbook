import { useEffect, useState } from 'react'

/**
 * Functional-component equivalent of ClassCounter.jsx: `useState` replaces
 * `this.state`/`setState`, and `useEffect` (with an empty dependency
 * array plus a cleanup return) replaces componentDidMount/
 * componentWillUnmount.
 */
function FunctionCounter() {
  const [count, setCount] = useState(0)

  useEffect(() => {
    // eslint-disable-next-line no-console
    console.log('FunctionCounter mounted')
    return () => {
      // eslint-disable-next-line no-console
      console.log('FunctionCounter will unmount')
    }
  }, [])

  const increment = () => setCount((c) => c + 1)
  const decrement = () => setCount((c) => c - 1)

  return (
    <div className="counter">
      <h3>Function Component Counter</h3>
      <p>Count: {count}</p>
      <button onClick={decrement}>-</button>
      <button onClick={increment}>+</button>
    </div>
  )
}

export default FunctionCounter
