import styled from 'styled-components';

const Container = styled.div`
  width: 80%;
  margin: 10px auto;
  // padding: 20px;
  background: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0,0,0,0.1);
`;

const Form = styled.form`
  display: flex;
  flex-direction: column;
`;

const Input = styled.input`
  // padding: 8px;
  margin-bottom: 15px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-family: 'LINESeedKR-Bd' !important;
`;

const TextArea = styled.textarea`
  // padding: 8px;
  width: 90%;
  height: auto;
  border: 1px solid #ccc;
  border-radius: 4px;
  min-height: 100px;
  margin-bottom: 15px;
  font-family: 'LINESeedKR-Bd' !important;
`;

const Fieldset = styled.fieldset`
  border: none;
  margin-bottom: 15px;
`;

const Legend = styled.legend`
  padding: 0;
  font-size: 1.1em;
  font-family: 'LINESeedKR-Bd' !important;
`;

const Button = styled.button`
  padding: 10px 15px;
  color: white;
  background-color: #007BFF;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;

  &:hover {
    background-color: #0056b3;
  }
`;

const Label = styled.label`
  margin-bottom: 5px;
  font-weight: bold;
  font-family: 'LINESeedKR-Bd' !important;
`;

export {Container, Form, Input, TextArea, Fieldset, Legend, Button, Label};