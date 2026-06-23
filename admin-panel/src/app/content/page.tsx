export default function ContentPage() {
  return (
    <div className="space-y-6">
      <div className="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-4">
        <div>
          <h1 className="text-2xl font-bold tracking-tight text-zinc-900 dark:text-zinc-50">Content Moderation</h1>
          <p className="text-sm text-zinc-500 dark:text-zinc-400 mt-1">Review, feature, or remove uploaded videos.</p>
        </div>
        <div className="flex items-center gap-3">
          <select className="border border-zinc-200 dark:border-zinc-800 bg-white dark:bg-zinc-900 rounded-lg text-sm px-3 py-2 outline-none focus:ring-2 focus:ring-blue-500 text-zinc-700 dark:text-zinc-200">
            <option>All Types</option>
            <option>Trending</option>
            <option>Reported</option>
          </select>
        </div>
      </div>

      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
        {[
          { title: "Dance Challenge #SummerVibes", creator: "@alex_j", views: "1.2M", date: "2 hours ago", reported: false },
          { title: "Cooking simple pasta in 10 mins", creator: "@chef_mario", views: "450K", date: "5 hours ago", reported: false },
          { title: "Look at my new cat!", creator: "@catlady99", views: "89K", date: "1 day ago", reported: false },
          { title: "Crypto trading tips (Must Watch)", creator: "@finance_bro", views: "12K", date: "2 days ago", reported: true },
          { title: "Beautiful sunset at the beach 🌅", creator: "@travel_sarah", views: "2.1M", date: "3 days ago", reported: false },
          { title: "Pranking my best friend 🤣", creator: "@prankster101", views: "340K", date: "4 days ago", reported: true },
          { title: "Quick workout routine at home", creator: "@fit_lifestyle", views: "115K", date: "5 days ago", reported: false },
          { title: "Reviewing the newest smartphone", creator: "@tech_guru", views: "500K", date: "1 week ago", reported: false },
        ].map((video, idx) => (
          <div key={idx} className="rounded-xl border border-zinc-200 dark:border-zinc-800 bg-white dark:bg-zinc-900 shadow-sm overflow-hidden flex flex-col">
            <div className="aspect-[9/16] bg-zinc-200 dark:bg-zinc-800 relative group overflow-hidden">
              <div className="absolute inset-0 flex items-center justify-center">
                <svg className="w-12 h-12 text-zinc-400 dark:text-zinc-600 opacity-50" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M14.752 11.168l-3.197-2.132A1 1 0 0010 9.87v4.263a1 1 0 001.555.832l3.197-2.132a1 1 0 000-1.664z" />
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                </svg>
              </div>
              {video.reported && (
                <div className="absolute top-3 right-3 bg-red-500 text-white text-xs font-bold px-2 py-1 rounded shadow">
                  Reported
                </div>
              )}
              <div className="absolute inset-0 bg-black/60 opacity-0 group-hover:opacity-100 transition-opacity flex flex-col justify-center gap-3 items-center backdrop-blur-sm">
                <button className="bg-white text-zinc-900 px-4 py-2 rounded-lg text-sm font-bold w-32 hover:bg-zinc-200">View Detail</button>
                <button className="bg-red-500 text-white px-4 py-2 rounded-lg text-sm font-bold w-32 hover:bg-red-600">Delete Video</button>
              </div>
            </div>
            <div className="p-4 flex-1 flex flex-col">
              <h3 className="font-medium text-zinc-900 dark:text-zinc-100 line-clamp-2 text-sm mb-2">{video.title}</h3>
              <div className="mt-auto flex items-center justify-between text-xs text-zinc-500 dark:text-zinc-400">
                <span className="font-medium text-blue-600 dark:text-blue-400">{video.creator}</span>
                <span>{video.date}</span>
              </div>
              <div className="mt-2 flex items-center justify-between text-xs text-zinc-500 dark:text-zinc-400 pt-2 border-t border-zinc-100 dark:border-zinc-800">
                <span className="flex items-center gap-1">
                  <svg className="w-3.5 h-3.5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z" />
                  </svg>
                  {video.views}
                </span>
                <span className="flex items-center gap-1">
                  <svg className="w-3.5 h-3.5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z" />
                  </svg>
                  Like / Share
                </span>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
