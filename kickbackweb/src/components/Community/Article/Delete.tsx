import axios from "axios";
import React, { useEffect } from "react";
import { useShallow } from "zustand/react/shallow";
import useAuthStore from "../../../stores/AuthStore";
import * as s from "../../../styles/Community/Article/Delete";

interface Props {
  id: Number;
  deleteArticle: () => void;
}

function Delete({ id, deleteArticle }: Props) {
  const { PATH } = useAuthStore(
    useShallow((state) => ({
      PATH: state.PATH,
    }))
  );

  const confirm = () => {
    axios
      .delete(
        `${PATH}/api/v1/board/${id}`,
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
        <div className="topBox">이 게시글을 삭제하시겠습니까?</div>
        <div className="bottomBox">
          <s.Btn className="delete" onClick={confirm}>
            삭제
          </s.Btn>
          <s.Btn className="cancle" onClick={deleteArticle}>
            취소
          </s.Btn>
        </div>
      </s.Wrapper>
    </s.Background>
  );
}

export default Delete;
