import { useState } from 'react';
import CarouselImg1 from '../assets/carousel-1.png';
import CarouselImg2 from '../assets/carousel-2.png';
import CarouselImg3 from '../assets/carousel-3.png';
import useAutoSlide from '../hooks/useAutoSlide';

const carouselImgs = [
  CarouselImg3,
  CarouselImg1,
  CarouselImg2,
  CarouselImg3,
  CarouselImg1,
];

// TODO: 1. 마우스광클 중복방지 -> 세마포어 기능 추가! -> priority: 하
// TODO: 2. pagination ellipsis 느리게 바뀌는 부분 -> useRef로 useState보다 먼저 바뀌게 만들면 빠를 듯  -> priority: 중
// TODO:    -> ref는 기본적으로 re-rendering을 하지 않으나 currentIdx와 연관지으면 코드를 재 평가하면서 ellipsis가 active될 듯 함.
// TODO: 3. mouseover시 autoSlide 멈춤 기능 추가!  -> priority: 상
// TODO: 4. draggable carousel 구현 -> priority: 하
function Carousel({ auto, infinite, carouselIntervalTime, transitionDelay }) {
  const transitionStyle = `all ease-in-out ${transitionDelay / 1000}s`;
  const [currentIdx, setCurrentIdx] = useState(1);
  const [isTransition, setIsTransition] = useState(true);
  const [isAutoSlideTrigger, setIsAutoSlideTrigger] = useState(auto);

  // carousel edge부분(양 끝) 눈속임 처리
  const replaceSlide = (targetCarouselIndex, delay) => {
    setTimeout(() => {
      setIsTransition(false);
      setCurrentIdx(targetCarouselIndex);
    }, delay);
  };

  const onSliderHandler = direction => {
    // currentIdx가 update되는 순간 transition이 끝났을 때 눈속임.
    // setCurrentIdx안에 쓴 이유는 prevState를 통해 state를 이전 상태임을 보장하기 위해서.
    setCurrentIdx(prev => {
      if (infinite) {
        if (direction === 'next' && prev + 1 === 4) {
          replaceSlide(1, transitionDelay);
        } else if (direction === 'prev' && prev - 1 === 0) {
          replaceSlide(3, transitionDelay);
        }
      }

      return direction === 'next'
        ? (prev + 1) % carouselImgs.length
        : (prev + carouselImgs.length - 1) % carouselImgs.length;
    });

    setIsTransition(true);
  };

  useAutoSlide(() => onSliderHandler('next'), carouselIntervalTime, isAutoSlideTrigger);

  // handle pagination ellipsis
  const onEllipsisClickHandler = index => setCurrentIdx(index);

  // mouse over/out과 달리 bubbling이 일어나지 않는 enter/leave를 이용함
  const onMouseEnterHandler = () => {
    setIsAutoSlideTrigger(false);
  };

  const onMouseLeaveHandler = () => {
    setIsAutoSlideTrigger(true);
  };

  return (
    <div
      onMouseEnter={onMouseEnterHandler}
      onMouseLeave={onMouseLeaveHandler}
      className="w-full max-w-limit relative overflow-hidden"
    >
      <div
        style={{
          transform: `translateX(-${currentIdx * 100}%)`,
          transition: isTransition ? transitionStyle : '',
        }}
        className="flex w-full"
      >
        {carouselImgs.map((imgSource, index) => (
          <img
            // eslint-disable-next-line react/no-array-index-key
            key={index}
            src={imgSource}
            alt={`carousel-img-${index + 1}`}
            className="object-cover"
          />
        ))}
      </div>
      <div className="absolute bottom-8 left-[47%] flex items-center gap-4 mr-2">
        {Array(carouselImgs.length - 2)
          .fill(0)
          .map((_, index) => (
            <div
              // eslint-disable-next-line react/no-array-index-key
              key={index}
              aria-label="CarouselPagination"
              role="button"
              onClick={() => onEllipsisClickHandler(index + 1)}
              className={`w-[0.5rem] h-[0.5rem] ${
                currentIdx === index + 1 ? 'bg-[#FFFFFF]' : 'bg-gray10/80'
              } rounded-full cursor-pointer`}
            />
          ))}
      </div>
      <div className="absolute top-1/2 left-0 w-full flex justify-between px-7">
        <button
          type="button"
          onClick={() => onSliderHandler('prev')}
          className="flex justify-center items-center rounded-full w-9 h-9 cursor-pointer hover:bg-[#E8E8E8] border-solid border-[1px] border-gray8"
        >
          <span className="text-gray6 text-xl">&larr;</span>
        </button>
        <button
          type="button"
          onClick={() => onSliderHandler('next')}
          className="flex justify-center items-center rounded-full w-9 h-9 cursor-pointer hover:bg-[#E8E8E8] border-solid border-[1px] border-gray8"
        >
          <span className="text-gray6 text-xl">&rarr;</span>
        </button>
      </div>
    </div>
  );
}

export default Carousel;

// useRef의 값의 변동은 setState보다 먼저 일어난다 -> 바로 일어난다.

// 1. 오류: autoSlide할 때 react 18에서 component 마운트 시 mount -> unmount -> mount와 같이
// mount가 두 번 일어나 setInterval이 두 번 등록되어 slide가 2칸 씩 이동했었음
// 해결: clean up function을 이용하여 첫 번째 마운트시 intervalId를 지워 줌

// 2. 오류: infinite carousel 구현시 마지막 엣지 슬라이드 부분에서 transition을 끄고 나면 추후에 transition을 다시 줄 수 없었음
// 해결: onSlideHandler에서 replaceSlide로 인해 transition이 제거됐어도 slide를 이동시키는
// 순간 setIsTransition(true)로 다시 transition을 넣어줌
