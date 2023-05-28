function Card({ children, flexItemwidth, className }) {
  return (
    <div
      style={{ flex: `1 1 ${flexItemwidth}`, maxWidth: flexItemwidth }}
      className={`flex flex-col py-2 ${className}`}
    >
      {children}
    </div>
  );
}

export default Card;
