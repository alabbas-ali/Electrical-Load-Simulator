//package his.loadprofile.config;
//
//import java.util.Locale;
//
//
//import org.springframework.context.MessageSource;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.support.ResourceBundleMessageSource;
//import org.springframework.web.servlet.LocaleResolver;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//import org.springframework.web.servlet.i18n.CookieLocaleResolver;
//import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
//
//@Configuration
//public class I18nConfiguration extends WebMvcConfigurerAdapter {
//
//	@Bean
//    public MessageSource messageSource() {
//        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
//        messageSource.setBasename("classpath:i18n/messages");
//        //messageSource.setBasename("classpath:i18n/messages");
//        messageSource.setDefaultEncoding("UTF-8");
//        return messageSource;
//    }
//	
//	@Bean
//	public LocaleResolver localeResolver() {
//		CookieLocaleResolver resolver = new CookieLocaleResolver();
//		resolver.setDefaultLocale(new Locale("en"));
//		resolver.setCookieName("myI18N_cookie");
//		return resolver;
//	}
//
//	@Override
//	public void addInterceptors(InterceptorRegistry reg) {
//		LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
//		interceptor.setParamName("locale");
//		reg.addInterceptor(interceptor);
//	}
//}
