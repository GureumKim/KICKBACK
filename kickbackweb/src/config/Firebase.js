// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getStorage } from "firebase/storage";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries


// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
  apiKey: "AIzaSyBl3jCSzXOkm8N1SOO6FXNsNMFbvWy_jl4",
  authDomain: "testset-58839.firebaseapp.com",
  projectId: "testset-58839",
  storageBucket: "testset-58839.appspot.com",
  messagingSenderId: "784857668398",
  appId: "1:784857668398:web:aa8db8a6322c341cc9b8bf",
  measurementId: "G-96C2YQWNX8"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const storage = getStorage(app);

export {storage};