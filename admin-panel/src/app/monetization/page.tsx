export default function MonetizationPage() {
  return (
    <div className="space-y-6">
      <div className="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-4">
        <div>
          <h1 className="text-2xl font-bold tracking-tight text-zinc-900 dark:text-zinc-50">Monetization & Payouts</h1>
          <p className="text-sm text-zinc-500 dark:text-zinc-400 mt-1">Manage platform revenue and content creator payouts.</p>
        </div>
        <button className="flex items-center justify-center gap-2 rounded-lg bg-emerald-600 px-4 py-2 text-sm font-medium text-white transition-colors hover:bg-emerald-700">
          <svg className="w-4 h-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4 16v1a3 3 0 003 3h10a3 3 0 003-3v-1m-4-4l-4 4m0 0l-4-4m4 4V4" />
          </svg>
          Export Report
        </button>
      </div>

      <div className="grid gap-6 sm:grid-cols-3">
        <div className="rounded-xl border border-zinc-200 dark:border-zinc-800 bg-white dark:bg-zinc-900 p-6 shadow-sm">
          <p className="text-sm font-medium text-zinc-500 dark:text-zinc-400">Total Platform Revenue</p>
          <div className="mt-2 flex items-baseline gap-2">
            <h3 className="text-3xl font-bold text-zinc-900 dark:text-zinc-50">$84,250.00</h3>
            <span className="text-sm font-medium text-emerald-500">+14.5%</span>
          </div>
        </div>
        <div className="rounded-xl border border-zinc-200 dark:border-zinc-800 bg-white dark:bg-zinc-900 p-6 shadow-sm">
          <p className="text-sm font-medium text-zinc-500 dark:text-zinc-400">Pending Payouts</p>
          <div className="mt-2 flex items-baseline gap-2">
            <h3 className="text-3xl font-bold text-zinc-900 dark:text-zinc-50">$12,400.00</h3>
            <span className="text-sm font-medium text-amber-500">23 Requests</span>
          </div>
        </div>
        <div className="rounded-xl border border-zinc-200 dark:border-zinc-800 bg-white dark:bg-zinc-900 p-6 shadow-sm">
          <p className="text-sm font-medium text-zinc-500 dark:text-zinc-400">Active Ad Campaigns</p>
          <div className="mt-2 flex items-baseline gap-2">
            <h3 className="text-3xl font-bold text-zinc-900 dark:text-zinc-50">45</h3>
            <span className="text-sm font-medium text-emerald-500">+5 This Week</span>
          </div>
        </div>
      </div>

      <div className="rounded-xl border border-zinc-200 dark:border-zinc-800 bg-white dark:bg-zinc-900 shadow-sm overflow-hidden">
        <div className="p-6 border-b border-zinc-200 dark:border-zinc-800 flex justify-between items-center">
          <h3 className="text-lg font-bold text-zinc-900 dark:text-zinc-50">Recent Payout Requests</h3>
        </div>
        <div className="overflow-x-auto">
          <table className="w-full text-left text-sm text-zinc-500 dark:text-zinc-400">
            <thead className="bg-zinc-50 dark:bg-zinc-800/50 text-xs uppercase text-zinc-700 dark:text-zinc-300">
              <tr>
                <th scope="col" className="px-6 py-4 font-medium">Creator</th>
                <th scope="col" className="px-6 py-4 font-medium">Amount</th>
                <th scope="col" className="px-6 py-4 font-medium">Method</th>
                <th scope="col" className="px-6 py-4 font-medium">Date</th>
                <th scope="col" className="px-6 py-4 font-medium">Status</th>
                <th scope="col" className="px-6 py-4 font-medium text-right">Action</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-zinc-200 dark:divide-zinc-800">
              {[
                { creator: '@alex_j', amount: '$450.00', method: 'PayPal', date: 'Oct 24, 2023', status: 'Pending' },
                { creator: '@travel_sarah', amount: '$1,200.00', method: 'Bank Transfer', date: 'Oct 23, 2023', status: 'Pending' },
                { creator: '@tech_guru', amount: '$890.00', method: 'Stripe', date: 'Oct 22, 2023', status: 'Processed' },
                { creator: '@chef_mario', amount: '$310.00', method: 'PayPal', date: 'Oct 21, 2023', status: 'Processed' },
                { creator: '@finance_bro', amount: '$5,000.00', method: 'Bank Transfer', date: 'Oct 20, 2023', status: 'Declined' },
              ].map((request, idx) => (
                <tr key={idx} className="hover:bg-zinc-50 dark:hover:bg-zinc-800/20 transition-colors">
                  <td className="px-6 py-4 font-medium text-zinc-900 dark:text-zinc-100">{request.creator}</td>
                  <td className="px-6 py-4 font-bold text-zinc-900 dark:text-zinc-200">{request.amount}</td>
                  <td className="px-6 py-4">{request.method}</td>
                  <td className="px-6 py-4">{request.date}</td>
                  <td className="px-6 py-4">
                    <span className={`inline-flex items-center rounded-full px-2.5 py-0.5 text-xs font-medium ${
                      request.status === 'Processed' ? 'bg-green-100 text-green-800 dark:bg-green-900/30 dark:text-green-400' :
                      request.status === 'Pending' ? 'bg-amber-100 text-amber-800 dark:bg-amber-900/30 dark:text-amber-400' :
                      'bg-red-100 text-red-800 dark:bg-red-900/30 dark:text-red-400'
                    }`}>
                      {request.status}
                    </span>
                  </td>
                  <td className="px-6 py-4 text-right">
                    {request.status === 'Pending' ? (
                      <button className="text-emerald-600 dark:text-emerald-400 font-medium hover:underline">Approve</button>
                    ) : (
                      <button className="text-zinc-500 hover:text-zinc-700 dark:hover:text-zinc-300 font-medium hover:underline">Details</button>
                    )}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}
