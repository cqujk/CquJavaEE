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


  return(
      <div>
        <h1>Login Page</h1>
        <form onSubmit={handleSubmit}>
          <input type="userId"
                 placeholder="请输入编号"
                 value={userId}
                 onChange={(e) => setUserId(e.target.value)}
                 required
          />
          <input type="password"
                 value={password}
                 onChange={(e) => setPassword(e.target.value)}
                 placeholder="password"
                 required
          />
          <button type="submit" disabled={loading}>
            {loading ? 'Loading...' : 'Login'}
          </button>
          {error && <p className="error">{error}</p>}
        </form>
        <button onClick={() => setIsRegisterModalOpen(true)}>注册</button>
        {isRegisterModalOpen && (
            <>
              <div
                  style={{
                    position: 'fixed',
                    top: 0,
                    left: 0,
                    width: '100%',
                    height: '100%',
                    backgroundColor: 'rgba(0, 0, 0, 0.5)',
                    zIndex: 1000
                  }}
              ></div>
              <div style={{
                position: 'fixed',
                top: '50%',
                left: '50%',
                transform: 'translate(-50%, -50%)',
                background: 'white',
                padding: '20px',
                boxShadow: '0 0 10px rgba(0,0,0,0.1)',
                zIndex: 1001
              }}>
                <h2>注册</h2>
                <form onSubmit={handleRegisterSubmit}>
                  <input type="text" id="registerUserId" placeholder="用户编号" required/>
                  <input type="password" id="registerPassword" placeholder="密码" required/>
                  <button type="submit">
                    {loading ? 'Loading...' : '注册'}
                  </button>
                  {error && <p className="error">{error}</p>}
                </form>
                <button onClick={() => setIsRegisterModalOpen(false)}>关闭</button>
              </div>
            </>
        )}
      </div>
  )
}