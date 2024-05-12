import React, { useState, useEffect, FormEvent } from "react";
import axios from "axios";
import useAuthStore from "../../stores/AuthStore";
import useUserStore from "../../stores/UserStore";
import { useShallow } from 'zustand/react/shallow';
import { useNavigate } from "react-router";
import {Container, Form, Input, TextArea, Fieldset, Legend, Button, Label} from "../../styles/Notice/AdminPage";

interface NoticeData {
    title: string;
    content: string;
}

const NoticeCreate: React.FC = () => {
  const navigate = useNavigate();
  const { isLogin, PATH } = useAuthStore(useShallow(state => ({ isLogin: state.isLogin, PATH: state.PATH })));
  const { nickname, role } = useUserStore(useShallow(state => ({ nickname: state.nickname, role: state.role })));

  const [title, setTitle] = useState<string>('');
  const [content, setContent] = useState<string>('');
  const [category, setCategory] = useState<'NOTICE' | 'UPDATE'>('NOTICE');

  // Redirect if not admin or not logged in
  // useEffect(() => {
  //   if (!isLogin || role !== 'ADMIN') {
  //     navigate('/');
  //   }
  // }, [isLogin, role, navigate]);

  const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    if (title && content && category) {
      try {
        const response = await axios.post(`${PATH}/api/v1/notice/create`, JSON.stringify({ title, content, category }), {
          headers: {
            'Content-Type': 'application/json'
          },
          withCredentials: true
        });
        console.log('Notice created:', response.data);
      } catch (error) {
        console.error('Failed to create notice:', error);
        alert('Failed to create the notice. Please try again.');
      }
    } else {
      alert('All fields including the category are required.');
    }
  };

  return (
    <Container>
      <h1>공지사항 작성</h1>
      <Form onSubmit={handleSubmit}>
        <div>
          <Label htmlFor="title">Title:</Label>
          <Input
            id="title"
            type="text"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            required
          />
        </div>
        <div>
          <Label htmlFor="content">Content:</Label>
          <TextArea
            id="content"
            value={content}
            onChange={(e) => setContent(e.target.value)}
            required
          />
        </div>
        <Fieldset>
          <Legend>Category</Legend>
          <Label>
            <Input
              type="radio"
              name="category"
              value="NOTICE"
              checked={category === 'NOTICE'}
              onChange={() => setCategory('NOTICE')}
            />
            Notice
          </Label>
          <Label>
            <Input
              type="radio"
              name="category"
              value="UPDATE"
              checked={category === 'UPDATE'}
              onChange={() => setCategory('UPDATE')}
            />
            Update
          </Label>
        </Fieldset>
        <Button type="submit">Submit</Button>
      </Form>
    </Container>
  );
}

export default NoticeCreate;
