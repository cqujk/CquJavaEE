'use client'
import {useState} from "react";
import Link from "next/link";
import Header from "@/components/Header"
export default function Layout({
                                   children,
                               }:{
    children: React.ReactNode;
}){
    const [count,setCount]=useState(0)
    return(
        <div>
            <nav>
                <Link href="/dashboard/about">About</Link>
            </nav>
            <h1>Dashboard Layout {count}</h1>
            <button onClick={()=>setCount(count+1)}>Add</button>
            {children}
            <Header/>
        </div>
    )
}

// export default function DashboardLayout({
//   children,
// }) {
//   return(
//       <section>
//           <nav>nss</nav>
//           {children}
//       </section>
//   )
// }