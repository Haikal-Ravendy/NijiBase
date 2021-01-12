# NijiCord
A Discord Bot that run Nijisanji Liver's Database. 

## Features

The bot currently supports the following commands:
- **`!whois [nickname/name]`**
  Get the information of the livers by using their nicknames or name
  ![](https://i.imgur.com/NjtBv5R.png)
- **`!add`** 
  Add new liver to the database by using Google Form (User with the role name of "Moderator" only)  
  ![](https://i.imgur.com/Ew8pT5t.png)
- **`!update [name/nickname]`**
  Update the status of 3D debut of the livers that goes by the nickname or name (User with the role name of "Moderator" only)
  ![](https://i.imgur.com/xj2c11R.png)
- **`!delete [name/nickname]`**
  Delete an existing liver with that name or nickname (User with the role name of "Moderator" only)
  ![](https://i.imgur.com/GhjTu1f.png)

## Installation

To run the bot for yourself, all you need to do is 
```
1. git clone https://github.com/Haikal-Ravendy/NijiCord.git
2. Create DB schema with the name of "nijibase"
3. Populate your database with the .sql file within /database SQL/
4. Rename .env.example to .env
5. Fill the .env with the token and the server to your database
```

## License

Nijicord is distributed under the [Apache license version 2.0](./LICENSE).
