'use client'
import React, {useState} from "react";
import { useRouter } from 'next/navigation';

export default function LoginPage() {
  const [userId, setUserId]=useState('');
  const [password, setPassword]=useState('');
  const [error,setError]=useState('');
  const [loading,setLoading]=useState(false);
  const [isRegisterModalOpen, setIsRegisterModalOpen] = useState(false);
  const router=useRouter();
  //push login request
  const handleSubmit=async(e:React.FormEvent)=>{
    e.preventDefault();
    setError('');
    setLoading(true);
    //console.log('即将发送登录请求...');
    try{
      const res=//await fetch('http://localhost:8080/login',{
          //await fetch('http://localhost:8081/auth/login',{
          await fetch('/auth/login',{
            method:'POST',
            headers:{
              'Content-Type':'application/json'
            },
            body:JSON.stringify({id:userId,password:password}),
          });
      //console.log('请求已发送');
      if(res.ok){
        // 登录成功，重定向到主页或其他页面
        const data=await res.json();
        localStorage.setItem('jwtToken',data.token);
        localStorage.setItem('userType',data.userType);
        localStorage.setItem('userId',userId);
        router.push('/home');
      }else{
        // 登录失败，显示错误信息
        const data=await res.json();
        setError(data.message);
      }
    }catch (err){

      setError('登录失败');
    }finally {
      setLoading(false);
    }
  };
  //push register request
  const handleRegisterSubmit =async(e:React.FormEvent)=>{
    e.preventDefault();
    setLoading(true);
    const userId = (document.getElementById('registerUserId') as HTMLInputElement).value;
    const password = (document.getElementById('registerPassword') as HTMLInputElement).value;
    // const userType= (document.getElementById('userType') as HTMLInputElement).value;
    try{
      const res=await fetch('http://localhost:8081/auth/register',{
        method:'POST',
        headers:{
          'Content-Type':'application/json'
        },
        body:JSON.stringify({id:userId,password:password}),
      });
      if(res.ok){
        alert('注册成功');
      }else{
        const data=await res.json();
        console.log(data);
        alert(data.message);
      }
    }catch (err){
      console.error(err);
      setError('注册ssss');
    }finally {
      setLoading(false);
    }
  };


  return (
      <div className="min-h-screen flex items-center justify-center bg-gray-100">
        <div className="bg-white p-8 rounded-lg shadow-md w-full max-w-md">
          <h1 className="text-2xl font-bold text-center mb-6">登录</h1>
          <form onSubmit={handleSubmit} className="space-y-4">
            <div>
              <label htmlFor="userId" className="block text-sm font-medium text-gray-700">用户编号</label>
              <input
                  type="text"
                  id="userId"
                  placeholder="请输入编号"
                  value={userId}
                  onChange={(e) => setUserId(e.target.value)}
                  required
                  className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
              />
            </div>
            <div>
              <label htmlFor="password" className="block text-sm font-medium text-gray-700">密码</label>
              <input
                  type="password"
                  id="password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  placeholder="密码"
                  required
                  className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
              />
            </div>
            <button
                type="submit"
                disabled={loading}
                className={`w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 ${loading ? 'cursor-not-allowed opacity-50' : ''}`}
            >
              {loading ? 'Loading...' : '登录'}
            </button>
            {error && <p className="text-red-500 text-sm text-center">{error}</p>}
          </form>
          <div className="mt-6 text-center">
            <button
                onClick={() => setIsRegisterModalOpen(true)}
                className="text-indigo-600 hover:text-indigo-500"
            >
              注册
            </button>
          </div>
        </div>

        {isRegisterModalOpen && (
            <div className="fixed inset-0 flex items-center justify-center bg-gray-800 bg-opacity-75 z-50">
              <div className="bg-white p-8 rounded-lg shadow-md w-full max-w-md">
                <h2 className="text-2xl font-bold mb-6">注册</h2>
                <form onSubmit={handleRegisterSubmit} className="space-y-4">
                  <div>
                    <label htmlFor="registerUserId" className="block text-sm font-medium text-gray-700">用户编号</label>
                    <input
                        type="text"
                        id="registerUserId"
                        placeholder="用户编号"
                        required
                        className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                    />
                  </div>
                  <div>
                    <label htmlFor="registerPassword" className="block text-sm font-medium text-gray-700">密码</label>
                    <input
                        type="password"
                        id="registerPassword"
                        placeholder="密码"
                        required
                        className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                    />
                  </div>
                  <button
                      type="submit"
                      disabled={loading}
                      className={`w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 ${loading ? 'cursor-not-allowed opacity-50' : ''}`}
                  >
                    {loading ? 'Loading...' : '注册'}
                  </button>
                  {error && <p className="text-red-500 text-sm text-center">{error}</p>}
                </form>
                <div className="mt-6 text-center">
                  <button
                      onClick={() => setIsRegisterModalOpen(false)}
                      className="text-indigo-600 hover:text-indigo-500"
                  >
                    关闭
                  </button>
                </div>
              </div>
            </div>
        )}
      </div>
  );
}