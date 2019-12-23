package space.delusive.discord.racoonsuperbot.config;

import lombok.SneakyThrows;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import space.delusive.discord.racoonsuperbot.discord.DiscordManager;
import space.delusive.discord.racoonsuperbot.discord.impl.DiscordManagerImpl;
import space.delusive.discord.racoonsuperbot.repository.TwitchStreamRepository;
import space.delusive.discord.racoonsuperbot.repository.YoutubeVideoRepository;
import space.delusive.discord.racoonsuperbot.repository.impl.TwitchStreamRepositoryImpl;
import space.delusive.discord.racoonsuperbot.repository.impl.YoutubeVideoRepositoryImpl;

@Configuration
@ComponentScan
@EnableScheduling
public class ApplicationConfiguration {

    @Bean
    DiscordManager getDiscordManager(
            @Value("${discord.message.pattern.youtube.video}") String youtubeVideoMessagePattern,
            @Value("${discord.message.pattern.twitch.stream}") String twitchStreamMessagePattern,
            @Value("${discord.channel.id}") String messageChannelId,
            @Autowired JDA jda) {
        return new DiscordManagerImpl(youtubeVideoMessagePattern, twitchStreamMessagePattern, messageChannelId, jda);
    }

    @Bean
    YoutubeVideoRepository getYoutubeVideoRepository(
            @Value("${youtube.api.token}") String apiToken,
            @Value("${youtube.api.search.url}") String searchUrl,
            @Value("${youtube.api.videos.from.playlist.url}") String videosFromPlaylistUrl) {
        return new YoutubeVideoRepositoryImpl(searchUrl, videosFromPlaylistUrl, apiToken);
    }

    @Bean
    TwitchStreamRepository getTwitchStreamRepository(
            @Value("${twitch.api.client.id}") String clientId,
            @Value("${twitch.api.streams.url}") String url) {
        return new TwitchStreamRepositoryImpl(url, clientId);
    }

    @Bean
    @SneakyThrows
    JDA getJda(@Value("${discord.bot.token}") String botToken) {
        return new JDABuilder(botToken).build();
    }

}
