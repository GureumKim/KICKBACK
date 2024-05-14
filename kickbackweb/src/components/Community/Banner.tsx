import * as s from "../../styles/Community/Board";
import commu from "../../assets/uphillTrack.png"

const Banner = () => {
  return (
    <>
      <s.IntroBox>
        <s.ImgBox>
          <img src={commu} alt="커뮤" />
          <div className="text">
            커뮤니티
          </div>
        </s.ImgBox>
      </s.IntroBox>
    </>
  );
};

export default Banner;
