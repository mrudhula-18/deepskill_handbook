import { Component } from 'react'

/**
 * ES6 class component demonstrating `this.state`, lifecycle methods, and
 * `render()` -- the "classic" way to write stateful React components,
 * shown side-by-side with the hooks equivalent in FunctionCounter.jsx.
 */
class ClassCounter extends Component {
  constructor(props) {
    super(props)
    this.state = { count: 0 }
  }

  componentDidMount() {
    // eslint-disable-next-line no-console
    console.log('ClassCounter mounted')
  }

  componentWillUnmount() {
    // eslint-disable-next-line no-console
    console.log('ClassCounter will unmount')
  }

  increment = () => {
    this.setState((prevState) => ({ count: prevState.count + 1 }))
  }

  decrement = () => {
    this.setState((prevState) => ({ count: prevState.count - 1 }))
  }

  render() {
    return (
      <div className="counter">
        <h3>Class Component Counter</h3>
        <p>Count: {this.state.count}</p>
        <button onClick={this.decrement}>-</button>
        <button onClick={this.increment}>+</button>
      </div>
    )
  }
}

export default ClassCounter
