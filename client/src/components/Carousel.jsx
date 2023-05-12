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

function Carousel() {
  const delay = 3000;
  const [currentIdx, setCurrentIdx] = useState(0);

  const onSliderClickHandler = () =>
    setCurrentIdx(prev => (prev + 1) % carouselImgs.length);

  useAutoSlide(onSliderClickHandler, delay);

  const onEllipsisClickHandler = index => setCurrentIdx(index);

  return (
    <div className="relative w-full overflow-hidden">
      <div
        style={{ transform: `translateX(-${currentIdx * 100}%)` }}
        className="flex w-full transition-all ease-in-out duration-500"
      >
        {carouselImgs.map((imgSource, index) => (
          <img
            // eslint-disable-next-line react/no-array-index-key
            key={index}
            src={imgSource}
            alt={`carousel-img-${index + 1}`}
          />
        ))}
      </div>
      <div className="absolute bottom-6 left-[45%] flex w-full">
        <button
          type="button"
          onClick={onSliderClickHandler}
          className="relative rounded-full w-[30px] h-[30px] cursor-pointer hover:bg-[#E8E8E8] border-solid border-[1px] border-gray8 mr-2"
        >
          <span className="absolute top-2 left-2">&larr;</span>
        </button>
        <div className="flex items-center gap-2 mr-2">
          {Array(carouselImgs.length)
            .fill(0)
            .map((_, index) => (
              <div
                // eslint-disable-next-line react/no-array-index-key
                key={index}
                aria-label="HandleCarousel"
                role="button"
                onClick={() => onEllipsisClickHandler(index)}
                className={`w-[6px] h-[6px] ${
                  currentIdx === index ? 'bg-[#FFFFFF]' : 'bg-gray10'
                } rounded-full cursor-pointer`}
              />
            ))}
        </div>
        <button
          type="button"
          onClick={onSliderClickHandler}
          className="relative rounded-full w-[30px] h-[30px] cursor-pointer hover:bg-[#E8E8E8] border-solid border-[1px] border-gray8"
        >
          <span className="absolute top-2 left-2">&rarr;</span>
        </button>
      </div>
    </div>
  );
}

export default Carousel;