import Link from 'next/link'

const Header =()=>{
    // return(
    //     <div className='flex justify-between items-center p-4'>
    //         <div className='flex items-center gap-4'>
    //             <Link href="/">Home</Link>
    //             <Link href="/about">About</Link>
    //         </div>
    //         <div className='flex items-center gap-4'>
    //             <Link href="/login">Login</Link>
    //             <Link href="/signup">Signup</Link>
    //         </div>
    //     </div>
    // )
    return(
        <div>
            <nav>
                <ul>
                    <li><Link href="/public">Home</Link></li>
                    <li><Link href="/about">About</Link></li>
                </ul>
            </nav>
        </div>
    )
}
export default Header;