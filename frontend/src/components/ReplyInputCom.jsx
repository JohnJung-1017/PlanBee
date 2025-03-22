import React from 'react'

const ReplyInputCom = ({commentText, setCommentText, handleSubmitComment}) => { //댓글입력에 대한 컴포넌트
  
  
    return (
    <div
      className="comment_input_container"
      style={{
        marginTop: "15px",
        background: "#ffffff",
        padding: "15px",
        borderRadius: "10px",
        display: "flex",
        flexDirection: "column",
        gap: "10px",
      }}
    >
      <textarea 
        className="comment_input"
        placeholder="댓글을 입력하세요..."
        value={commentText}
        onChange={(e) => setCommentText(e.target.value)}
        style={{
          width: "100%",
          padding: "10px",
          border: "1px solid #ddd",
          borderRadius: "10px",
          fontSize: "15px",
          resize: "vertical",
          fontFamily: "inherit"
        }}
      />
      <button 
        className="comment_submit"
        style={{
          alignSelf: "flex-end",
          padding: "8px 16px",
          background: "#f0f0f0",
          border: "none",
          borderRadius: "10px",
          cursor: "pointer",
          fontSize: "14px"
        }}
      >
        댓글 등록
      </button>
    </div>
  )
}

export default ReplyInputCom
