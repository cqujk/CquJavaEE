
const fetchWithAuth = async (url: string, options: RequestInit = {}): Promise<Response> => {
    const token = localStorage.getItem('jwtToken');

    const headers = new Headers(options.headers || {});
    if (token) {
        headers.append('Authorization', `Bearer ${token}`);
    }

    const response = await fetch(url, {
        ...options,
        headers
    }as RequestInit);

    if (!response.ok) {
        //重定向到首页，并清除登陆状态
        // localStorage.removeItem('jwtToken');
        // window.location.href = '/';
        console.log('HTTP: '+response);
        //throw new Error(`HTTP error! Status: ${response.status}`);
    }

    return response;
};

export default fetchWithAuth;
