const tool = {
    // 用來存放1.用戶是否已加載全部訊息2.用戶的聊天訊息
    chatCache: {},
    checkCache(username) {
        if (!this.chatCache[username]) {
            this.chatCache[username] = {
                isLoadingCompleted: false,
                content: []
            }
        }
    },
    getCache(username) {
        this.checkCache(username);
        return this.chatCache[username].content;
    },
    addMsg(username, newContent) {
        this.checkCache(username);
        this.chatCache[username].content = [...this.chatCache[username].content, ...newContent]
        this.chatCache[username].content.sort((a, b) => a.seq - b.seq)
    },
    complete(username) {
        this.chatCache[username].isLoadingCompleted = true;
    },
    isCompleted(username) {
        return this.chatCache[username].isLoadingCompleted;
    }
}
