import Articles from './Article/Articles';

interface Props {
  now: string;
}

function Board({now}: Props) {
  return (
    <>
    {now === "Article" ? <Articles /> : "qna"}
    </>
    )
}

export default Board