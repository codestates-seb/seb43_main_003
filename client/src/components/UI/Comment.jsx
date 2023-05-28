function Comment() {
  return (
    <div className="w-[50rem] flex flex-col border-solid border-b-[1px] border-gray12 py-[22px]">
      <p className="font-medium text-[15px] text-black3">comment.content</p>
      <div className="w-full flex justify-between mt-[7px]">
        <p className="font-normal text-[12px] text-gray6">content.title</p>
        <p className="font-normal text-[12px] text-gray6 leading-4">createAt</p>
      </div>
    </div>
  );
}

export default Comment;
