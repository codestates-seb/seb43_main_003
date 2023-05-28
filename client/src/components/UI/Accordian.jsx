import Button from './Button';

function Accordian({
  visible,
  onClickHandler,
  activeValue,
  itemList,
  className,
  activeColor,
}) {
  return (
    <div className="flex flex-col">
      {visible &&
        itemList.map((value, index) => (
          <Button
            // eslint-disable-next-line react/no-array-index-key
            key={index}
            onClick={() => onClickHandler(value)}
            className={`${className} ${activeValue === value ? activeColor : 'inherit'}`}
          >
            {value}
          </Button>
        ))}
      {}
    </div>
  );
}

export default Accordian;

// eslintreact/jsx-no-useless-fragment나는 경우 -> https://codesandbox.io/s/qw6cj
// {}를 이용하여 lint에러를 해결함
