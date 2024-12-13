import fetchWithAuth from "@/lib/api/fetchWithAuth";

export const handleLogout = async (userId: string): Promise<void> => {
    try {
        const response = await fetchWithAuth('/auth/logout', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ userId }),
        });
        console.log('Response:', response);
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        // 清除会话存储或重定向到登录页面
        localStorage.removeItem('jwToken');
        localStorage.removeItem('userId');
        localStorage.removeItem('userType');
        window.location.href = '/';
    } catch (error) {
        console.error('Logout failed:', error);
        alert('注销失败，请重试。');
    }
};
