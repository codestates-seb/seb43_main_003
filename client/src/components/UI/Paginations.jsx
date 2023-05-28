import Button from './Button';
import { ChevronLeftIcon, ChevronRightIcon } from '../Icons';

function Pagination({
  pagePerView,
  activeColor,
  postTotalLength,
  paginationValue,
  onPaginationButtonClickHandler,
}) {
  const numList = Array(parseInt(postTotalLength / pagePerView, 10) + 1)
    .fill(0)
    .map((_, index) => index + 1);

  return (
    <div className="flex items-center gap-5">
      <Button
        onClick={() => {
          onPaginationButtonClickHandler(
            paginationValue - 1 < numList[0] ? numList.at(-1) : paginationValue - 1,
          );
        }}
        className="cursor-pointer"
      >
        <ChevronLeftIcon className="relative w-3 h-3 top-[2px]" />
      </Button>
      {numList.map(num => (
        <Button
          onClick={() => onPaginationButtonClickHandler(num)}
          // eslint-disable-next-line react/no-array-index-key
          key={num}
          value={num + 1}
          className={paginationValue === num ? activeColor : 'text-inherit'}
        >
          {num}
        </Button>
      ))}
      <Button
        onClick={() =>
          onPaginationButtonClickHandler(
            paginationValue + 1 > numList.at(-1) ? numList[0] : paginationValue + 1,
          )
        }
        className="cursor-pointer"
      >
        <ChevronRightIcon className="relative w-3 h-3 top-[1px]" />
      </Button>
    </div>
  );
}

export default Pagination;
