import * as s from "../../styles/Community/Board";

interface Props {
  now: string;
}

const Banner = ({ now }: Props) => {
  return (
    <>
      <s.IntroBox>
        <s.ImgBox>
          <img src="" alt="" />
          <div className="text">
            커뮤니티 - {now === "Article" ? "자유 게시판" : "QnA"}
          </div>
        </s.ImgBox>
      </s.IntroBox>
    </>
  );
};

export default Banner;
