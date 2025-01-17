const render = {
    genMessage(messageVo, me) {
        const wrap = document.createElement('div');
        wrap.classList.add("message");
        if(me === messageVo.sender) {wrap.classList.add("me")}
        const text = document.createElement('pre');
        text.classList.add('message-text');
        text.textContent = messageVo.content;
        const status = document.createElement('div');
        status.classList.add("message-status");
        const read = document.createElement('div');
        if (messageVo.status === "READ") {
            read.textContent = "已讀";
        }
        const time = document.createElement('div');
        const date = new Date(messageVo.sendTime);
        time.textContent = date.getHours() + ":" + date.getMinutes()

        // 組裝
        status.appendChild(time);
        status.appendChild(read);
        wrap.appendChild(text);
        wrap.appendChild(status);
        return wrap;
    },
    genWaitMessage(messageVo) {
        const wrap = document.createElement('div');
        wrap.classList.add("message", "me");
        wrap.id = messageVo.sendId;
        const text = document.createElement('pre');
        text.classList.add('message-text');
        text.textContent = messageVo.content;
        // 組裝
        wrap.appendChild(text);
        return wrap;
    },
    genChatListItem(username, handleClick){
        const item = document.createElement('div');
        item.classList.add('chat-list-item');
        item.addEventListener('click', e =>{
            // changeChatroom(username);
            handleClick(e);
        })
        const name = document.createElement('p');
        name.innerText = username;
        item.appendChild(name);
        return item;
    },
    // 重新重記憶體內渲染整個聊天紀錄
    history(arr, me) {
        chatHistory.innerHTML = "";
        arr.forEach(i => {
            chatHistory.appendChild(this.genMessage(i, me));
        })
    }
}
// <div className="message">
//     <pre className="message-text">你好唷</pre>
//     <div className="message-status">
//         <div>已讀</div>
//         <div>12:00</div>
//     </div>
// </div>
// content
//     :
//     "hihi"
// sendTime
//     :
//     "2025-01-07T07:10:49.221349Z"
// sender
//     :
//     "yanren"
// seq
//     :
//     1
// status
//     :
//     "UNREAD"
// type
//     :
//     "TEXT"