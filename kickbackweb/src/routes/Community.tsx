import { useParams } from "react-router";
import Banner from "../components/Community/Banner";
import Tab from "../components/Community/Tab";
import Board from "../components/Community/Board";

function Community() {
  const param = useParams();

  return (
    <>
      {(param.type === "QnA" || param.type === "Article") ? (
        <>
          <Banner now={param.type}/>
          <Tab now={param.type} />
          <Board now={param.type} />
        </>
      ) : "404"}
    </>
  );
}

export default Community;
