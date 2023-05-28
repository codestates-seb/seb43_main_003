function Button({ value, onClick, className, children }) {
  return (
    <button
      type="button"
      onClick={onClick}
      value={value}
      className={`flex items-center ${className}`}
    >
      {children}
    </button>
  );
}

export default Button;
