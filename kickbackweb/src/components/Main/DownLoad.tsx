import React, {useState} from 'react';
import { ref, getDownloadURL } from "firebase/storage";
import { storage } from "../../config/Firebase.js";

interface DownloadButtonProps {
  filename: string;
}

const DownloadButton: React.FC<DownloadButtonProps> = ({ filename }) => {
  const [error, setError] = useState<string | null>(null);
  
  const handleDownload = () => {
    const fileRef = ref(storage, `${filename}`);

    getDownloadURL(fileRef)
      .then((url) => {
        fetch(url)
          .then(response => response.blob())
          .then(blob => {
            const downloadUrl = window.URL.createObjectURL(blob);
            const link = document.createElement('a');
            link.href = downloadUrl;
            link.download = filename;
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
            window.URL.revokeObjectURL(downloadUrl);
          });
      })
      .catch((error) => {
        console.error('Error downloading the file:', error);
      });
  };

  return (
    <>
        <button onClick={handleDownload} style={{ padding: "10px 20px", fontSize: "16px", cursor: "pointer" }}>
          Download Image
        </button>
         {error && <p style={{ color: 'red' }}>{error}</p>}
    </>
  );
};

export default DownloadButton;