import { useDispatch, useSelector } from 'react-redux';

import { useEffect } from 'react';
import {
  fetchPopularDevelopmentsAction,
  fetchRealTimeDevelopmentsAction,
  fetchAllDevelopmentsAction,
} from '../store/developmentSlice';

import Carousel from '../components/Carousel';
import Card from '../components/UI/Card';
import Item from '../components/Item';

const CaurouselConfig = {
  auto: true,
  infinite: true,
  carouselIntervalTime: 3000,
  transitionDelay: 500,
};

function Home() {
  const { popularRanking, realTimeRanking, allDevelopments } = useSelector(
    state => state.developments,
  );
  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(fetchPopularDevelopmentsAction());
    dispatch(fetchRealTimeDevelopmentsAction());
    dispatch(fetchAllDevelopmentsAction());
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <>
      <div className="w-full flex justify-center pt-36 bg-gray3">
        <Carousel {...CaurouselConfig} />
      </div>
      <div className="w-full flex justify-center bg-white1">
        <div className="flex w-full justify-between max-w-limit pt-24 pb-32 animate-fadeIn-up">
          <h2 className="text-4xl font-bold">개발자를 위한 공간.</h2>
          <div className="flex flex-col text-sm font-normal max-w-[26rem] tracking-[0.04rem] space-y-[0.5rem]">
            <p>
              이곳은 개발자들을 위한 <span className="inline font-extrabold">공간</span>
              입니다.
            </p>
            <p>자유롭게 탐색하고, 유용한 정보들을 찾아가세요. 당신의 지식을</p>
            <p>나누어주세요. 개발에 대한 정보라면 어떤 것이든 환영합니다.</p>
          </div>
        </div>
      </div>
      {/* flex flex-col 같이 써주어야 items-center 먹음 */}
      <div className="w-full flex flex-col py-36 items-center bg-gray1">
        <div className="w-full flex flex-col max-w-limit">
          <h3 className="text-[1.6rem] font-bold  mb-[2rem]">실시간 순위</h3>
          <div className="w-full flex justify-between gap-8">
            {realTimeRanking.data.map(info => (
              <Card key={info.postId} flexItemwidth="33%">
                <Item {...info} />
              </Card>
            ))}
          </div>
        </div>
        <div className="w-full flex flex-col max-w-limit mt-32">
          <h3 className="text-[1.6rem] font-bold  mb-[2rem]">인기 게시물</h3>
          <div className="w-full flex justify-between gap-8">
            {popularRanking.data.map((info, index) => (
              <Card key={info.postId} flexItemwidth="33%">
                <h1 className="pb-3 mb-7 text-lg font-medium border-b-[1px] border-solid border-gray4">
                  {/* eslint-disable-next-line no-nested-ternary */}
                  {index === 0 ? '글' : index === 1 ? '영상' : '트렌드'}
                </h1>
                <Item {...info} />
              </Card>
            ))}
          </div>
        </div>
        <div className="w-full flex flex-col max-w-limit mt-32">
          <h3 className="text-[1.6rem] font-bold  mb-[2rem]">
            당신이 찾고 있던, 그 장비
          </h3>
          <div className="w-full flex justify-between gap-8">
            {popularRanking.data.map(info => (
              <Card key={info.postId} flexItemwidth="33%">
                <Item {...info} />
              </Card>
            ))}
          </div>
        </div>
      </div>
    </>
  );
}

export default Home;
