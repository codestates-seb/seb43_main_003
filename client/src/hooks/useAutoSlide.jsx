import { useEffect } from 'react';

const autoSlideInterval = (effectCallback, delay) => {
  const intervalId = setInterval(effectCallback, delay);

  return intervalId;
};

function useAutoSlide(effectCallback, delay, trigger) {
  useEffect(() => {
    if (trigger) {
      const intervalId = autoSlideInterval(effectCallback, delay);

      return () => clearInterval(intervalId);
    }

    return () => {};
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [trigger]);
}

export default useAutoSlide;
