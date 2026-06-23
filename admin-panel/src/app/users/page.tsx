export default function UsersPage() {
  return (
    <div className="space-y-6">
      <div className="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-4">
        <div>
          <h1 className="text-2xl font-bold tracking-tight text-zinc-900 dark:text-zinc-50">Users Management</h1>
          <p className="text-sm text-zinc-500 dark:text-zinc-400 mt-1">Manage platform users, creators, and their accounts.</p>
        </div>
        <div className="flex items-center gap-3">
          <div className="relative">
            <svg className="w-4 h-4 absolute left-3 top-1/2 -translate-y-1/2 text-zinc-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
            </svg>
            <input 
              type="text" 
              placeholder="Search users..." 
              className="pl-9 pr-4 py-2 border border-zinc-200 dark:border-zinc-800 bg-white dark:bg-zinc-900 rounded-lg text-sm outline-none focus:ring-2 focus:ring-blue-500 w-full md:w-64 dark:text-zinc-200"
            />
          </div>
          <button className="flex items-center justify-center gap-2 rounded-lg bg-blue-600 px-4 py-2 text-sm font-medium text-white transition-colors hover:bg-blue-700">
            <svg className="w-4 h-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
               <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 4v16m8-8H4" />
            </svg>
            Add User
          </button>
        </div>
      </div>

      <div className="rounded-xl border border-zinc-200 dark:border-zinc-800 bg-white dark:bg-zinc-900 shadow-sm overflow-hidden">
        <div className="overflow-x-auto">
          <table className="w-full text-left text-sm text-zinc-500 dark:text-zinc-400">
            <thead className="bg-zinc-50 dark:bg-zinc-800/50 text-xs uppercase text-zinc-700 dark:text-zinc-300">
              <tr>
                <th scope="col" className="px-6 py-4 font-medium">User Details</th>
                <th scope="col" className="px-6 py-4 font-medium">Role</th>
                <th scope="col" className="px-6 py-4 font-medium">Status</th>
                <th scope="col" className="px-6 py-4 font-medium">Followers</th>
                <th scope="col" className="px-6 py-4 font-medium text-right">Actions</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-zinc-200 dark:divide-zinc-800">
              {[
                { name: 'Alex Johnson', username: '@alex_j', email: 'alex@example.com', role: 'Creator', status: 'Active', followers: '124K', img: 'A' },
                { name: 'Maria Garcia', username: '@maria_g', email: 'maria@example.com', role: 'User', status: 'Active', followers: '230', img: 'M' },
                { name: 'James Wilson', username: '@j_wilson', email: 'james@example.com', role: 'Creator', status: 'Suspended', followers: '45K', img: 'J' },
                { name: 'Linda Brown', username: '@linda_b', email: 'linda@example.com', role: 'Admin', status: 'Active', followers: '1.2K', img: 'L' },
                { name: 'Michael Davis', username: '@mike_d', email: 'michael@example.com', role: 'User', status: 'Active', followers: '56', img: 'M' },
              ].map((user, idx) => (
                <tr key={idx} className="hover:bg-zinc-50 dark:hover:bg-zinc-800/20 transition-colors">
                  <td className="px-6 py-4">
                    <div className="flex items-center gap-3">
                      <div className="h-10 w-10 rounded-full bg-blue-100 dark:bg-blue-900/50 flex items-center justify-center text-blue-600 dark:text-blue-400 font-bold text-sm">
                        {user.img}
                      </div>
                      <div>
                        <div className="font-medium text-zinc-900 dark:text-zinc-100">{user.name}</div>
                        <div className="text-xs text-zinc-500 mb-0.5">{user.username}</div>
                        <div className="text-xs text-zinc-400 dark:text-zinc-500">{user.email}</div>
                      </div>
                    </div>
                  </td>
                  <td className="px-6 py-4 text-zinc-900 dark:text-zinc-300 font-medium">{user.role}</td>
                  <td className="px-6 py-4">
                    <span className={`inline-flex items-center rounded-full px-2.5 py-0.5 text-xs font-medium ${
                      user.status === 'Active' 
                        ? 'bg-green-100 text-green-800 dark:bg-green-900/30 dark:text-green-400' 
                        : 'bg-red-100 text-red-800 dark:bg-red-900/30 dark:text-red-400'
                    }`}>
                      {user.status}
                    </span>
                  </td>
                  <td className="px-6 py-4 font-medium text-zinc-900 dark:text-zinc-200">{user.followers}</td>
                  <td className="px-6 py-4 text-right">
                    <button className="text-zinc-500 dark:text-zinc-400 hover:text-blue-600 dark:hover:text-blue-400 mx-2 transition-colors">
                      Edit
                    </button>
                    <button className="text-zinc-500 dark:text-zinc-400 hover:text-red-600 dark:hover:text-red-400 mx-2 transition-colors">
                      Ban
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
        <div className="p-4 border-t border-zinc-200 dark:border-zinc-800 flex items-center justify-between text-sm">
          <span className="text-zinc-500 dark:text-zinc-400">Showing 1 to 5 of 14,231 entries</span>
          <div className="flex gap-2">
            <button className="px-3 py-1 border border-zinc-200 dark:border-zinc-800 rounded text-zinc-600 dark:text-zinc-400 hover:bg-zinc-50 dark:hover:bg-zinc-800 transition-colors cursor-not-allowed opacity-50">Previous</button>
            <button className="px-3 py-1 border border-zinc-200 dark:border-zinc-800 rounded text-zinc-600 dark:text-zinc-400 hover:bg-zinc-50 dark:hover:bg-zinc-800 transition-colors">Next</button>
          </div>
        </div>
      </div>
    </div>
  );
}
