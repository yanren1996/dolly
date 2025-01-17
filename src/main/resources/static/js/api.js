const api = {
    /**
     * 取得伺服器所有用戶列表
     *
     * @returns {Promise<any>}
     */
    async getAllUser() {
        const res = await fetch("api/get-others-users");
        return await res.json();
    },

    /**
     * 取得聊天紀錄
     *
     * @param activeChatUserId  用戶與誰的聊天紀錄
     * @param lastSeq           瀏覽器暫存裡最舊的一則訊息流水號
     * @returns {Promise<any>}  HistoryVo
     */
    async getChatHistory(activeChatUserId, lastSeq = -1) {
        const res = await fetch(`api/get-chat-history?id=${activeChatUserId}&lastSeq=${lastSeq}`);
        return await res.json();
    },

}
