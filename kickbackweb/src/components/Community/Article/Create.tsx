import React, { useEffect, useState } from "react";
import axios from "axios";
import { useShallow } from "zustand/react/shallow";
import useAuthStore from "../../../stores/AuthStore";
import * as s from "../../../styles/Community/Article/Create";

interface Props {
  createArticle: () => void;
}

function Create({ createArticle }: Props) {
  const { PATH } = useAuthStore(
    useShallow((state) => ({
      PATH: state.PATH,
    }))
  );

  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");

  const changeTitle = (event: React.ChangeEvent<HTMLInputElement>) => {
    setTitle(event.target.value);
  };

  const changeContent = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
    setContent(event.target.value);
  };

  const onSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    axios
      .post(
        `${PATH}/api/v1/board/create`,
        {
          title,
          content,
          category: "FREE",
        },
        {
          withCredentials: true,
        }
      )
      .then(() => {
        window.location.reload();
      })
      .catch((error) => {
        console.log(error);
      });
  };

  useEffect(() => {
    document.body.style.overflow = "hidden";

    return () => {
      document.body.style.overflow = "auto";
    };
  }, []);
  return (
    <s.Background>
      <s.Wrapper>
        <s.TopBox>
          <s.Close onClick={() => createArticle()}>x</s.Close>
        </s.TopBox>
        <form onSubmit={onSubmit}>
          <s.Title
            placeholder="제목을 입력해 주세요."
            value={title}
            onChange={changeTitle}
          ></s.Title>
          <s.Content
            placeholder="내용을 입력해 주세요."
            value={content}
            onChange={changeContent}
          ></s.Content>
          <s.Submit>작성하기</s.Submit>
        </form>
      </s.Wrapper>
    </s.Background>
  );
}

export default Create;
