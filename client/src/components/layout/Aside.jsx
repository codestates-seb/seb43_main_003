import { useState } from 'react';
import { NavLink } from 'react-router-dom';

function Aside({ title, categories, activeValue, onFilterClickHandler }) {
  const profileNavActiveStyle = ({ isActive }) =>
    `text-[14px] font-normal cursor-pointer ${isActive && 'text-activeBlue'}`;

  return (
    <div className="sticky top-44 mt-main-top w-[13rem] mr-[1rem] pb-28">
      <div className="w-[7.3rem] border-b-[3px] border-solid border-black3 font-bold text-xl text-black3 pb-[15px] mb-[2rem]">
        {title}
      </div>
      <div>
        {categories?.titles?.map((title, index) => {
          return (
            // eslint-disable-next-line react/no-array-index-key
            <div key={index} className="flex flex-col items-start py-[15px]">
              {categories?.path && (
                <NavLink to={categories?.path[index]} className={profileNavActiveStyle}>
                  {title}
                </NavLink>
              )}
              {/* 전체개발페이지처럼 filter기능이라 보내줄 path가 없을 시 */}
              {!categories?.path && (
                // eslint-disable-next-line jsx-a11y/no-static-element-interactions
                <div
                  onClick={() => onFilterClickHandler(title)}
                  className={profileNavActiveStyle({ isActive: title === activeValue })}
                >
                  {title}
                </div>
              )}
            </div>
          );
        })}
      </div>
    </div>
  );
}

export default Aside;
