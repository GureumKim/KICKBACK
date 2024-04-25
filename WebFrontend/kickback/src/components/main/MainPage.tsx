import { Navigate } from "react-router-dom";

function MainPage() {
  const isUserValid = false;

  return (
    <>{isUserValid ? <Navigate to={"notice"} /> : <Navigate to={"login"} />}</>
  );
}

export default MainPage;
