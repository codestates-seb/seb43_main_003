import SadCatImg from '../assets/notFoundCat.png';

function PageNotFound() {
  return (
    <div className="flex flex-col items-center my-[10rem]">
      <h1 className="text-[5rem] text-gray6 font-bold underline mb-[1rem]">
        Page Not Found
      </h1>
      <img src={SadCatImg} alt="" />
    </div>
  );
}

export default PageNotFound;
