import { Link } from 'react-router-dom';

import { StarIcon, UnionIcon } from './Icons';

function Item(props) {
  const {
    postId,
    title,
    sourceMedia,
    thumbnailImage,
    starAvg,
    recommends,
    author,
    sorta,
    sourceURL,
    tags,
  } = props;

  return (
    <Link to={sourceURL}>
      <div className="w-full h-[12rem] flex justify-center items-center bg-yellow-100">
        <img
          src={thumbnailImage}
          className="w-full h-full object-cover"
          alt="썸네일 및 대표 사진"
        />
      </div>
      <div className="w-full h-[10.5rem] bg-white p-[26px]">
        <div>
          <p className="text-[12px] text-gray4 font-semibold">{sourceMedia}</p>
          <div className="w-full h-[4.6rem] text-[17px] text-black3 font-bold border-b-[1px] border-solid border-gray2 mt-[13px]">
            {title}
          </div>
        </div>
        <div className="flex justify-between items-center mt-[13px]">
          <div className="flex items-center">
            <img
              className="w-[21px] h-[21px] rounded-full mr-[10px]"
              src={author.profileImage}
              alt="유저이미지"
            />
            <p className="text-[12px] text-gray4">{author.name}</p>
          </div>
          <div className="flex items-center">
            <div className="flex items-center">
              <StarIcon className="mr-[9px] mb-[3px]" />
              <p className="text-[12px] text-gray4 mr-[15px]">{starAvg}</p>
            </div>
            <div className="flex item-center">
              <UnionIcon className="mr-[9px] mb-[3px]" />
              <p className="text-[12px] text-gray4 mt-[2px]">
                {recommends >= 0 ? recommends : props.recommend}
              </p>
            </div>
          </div>
        </div>
      </div>
    </Link>
  );
}

export default Item;
