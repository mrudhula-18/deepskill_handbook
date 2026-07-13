/**
 * Demonstrates two common conditional-rendering idioms:
 *  - ternary (`cond ? a : b`) for an either/or message
 *  - inline `&&` for rendering something only when a condition is true
 */
function ConditionalMessage({ count }) {
  return (
    <p className="conditional-message">
      {count === 0
        ? 'No books yet - add your first one below.'
        : `${count} book${count === 1 ? '' : 's'} in your library.`}
      {count > 10 && <span className="badge"> You are a serious reader!</span>}
    </p>
  )
}

export default ConditionalMessage
