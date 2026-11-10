<h1 align="center">Iteration 1</h1>

## What do I want to do?
In my VERY first iteration, I want something simple: just a way to record my mood for a specific date. I also want to be able to look back at the moods of that date, and change my mind later. If at the end of the day a mood doesn't feel right anymore, I can delete it or add a new one.

🎀 Monthly calendar

🎀 Record morning/afternoon/night mood

## How will I do it?

### Use Room for local database

This is an app I'll open every single day, so it has to be there for me anytime, with or without internet 💗 Storing everything on the phone keeps it fast, simple, and always available.

Syncing across multiple devices would be nice someday, but it's not needed for now. If I get there, the local database stays the main place for my moods, and the internet part is just an add-on.

In this iteration, the database is still small. I only create 1 table called: ```mood_entry```

| column | type | note |
|---|---|---|
| uid | Int | auto-generated |
| date | String | the day of the mood. Stored as `yyyyMMdd` (e.g. `20261110`). It's simple, easy to read, and I only care about the day, not the exact time. |
| mood | String (enum Mood) | the mood I picked.  Moods are an enum in the code, but stored as text in the database (via a Room type converter). Using an enum keeps the code tidy and makes it easy to add new moods later.|

You're probably wondering, "you said your mood shifts between morning, afternoon, and night, so why didn't you store the exact time too?"

Now, now. That's exactly why I picked 3, but that's where it ends. I care about *what* I felt, not *when* down to the minute. Saving hours, minutes, and seconds would turn this app into a strict diary with a schedule, and I don't want that.

A day just holds up to 3 moods, in whatever order they came 🧸
 
### Samsung Calendar as my UI inspiration

I'm gonna be very honest with you: UI/UX is NOT my strong suit. It's hard! Why can't I just use whatever pink colors I want? Why can't I use a heart shape for a button? Ridiculous! I feel like my cutesy creativity is being trapped.

But, as a good Software Engineer, I can't be selfish. I need to be mindful for all kinds of users. Because even though right now the target audience is only me, I want my app to be usable by lots of people 🎁 So, that's why I try to find the closest app to what I imagine that could help me envision how my app is gonna look. And Samsung Calendar is my go-to! Samsung Calendar is familiar to a lot of people, so borrowing its layout means nobody has to learn how my app works.

#### Calendar view

#### Mood form dialog

##### Select a certain mood

#### Mood detail dialog

#### When there are already 3 moods for a day

##### Select a certain mood

#### Empty mood detail dialog

### Up in the air

blablabla

## When can I celebrate?

- [x] I can open the calendar and see the current month
- [x] I can tap a date and add a mood for morning / afternoon / night
- [x] I can see the moods I saved for that date
- [x] I can delete a mood and add a new one
- [x] I close the app, open it again, and my moods are still there

## How did it go?
- Yang berhasil:
- Yang macet:
- Yang aku pelajari:
- Dipindah ke iterasi berikutnya:
