import { useNavigate } from "react-router-dom";
import * as s from "../../styles/Community/Tab";

interface Props {
  now: string;
}

function Tab({now}: Props) {
  const navigate = useNavigate();

  return (
    <s.Tab>
      <s.menu className={now === "Article" ? "activate" : ""} onClick={() => navigate("/community/Article")}>자유 게시판</s.menu>
      <s.menu className={now === "QnA" ? "activate" : ""} onClick={() => navigate("/community/QnA")}>Q & A</s.menu>
    </s.Tab>
  );
}

export default Tab;
