package com.example.headless_file_sharing;

import com.example.headless_file_sharing.presentation.TerminalInterface;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HeadlessFileSharingApplication {

	static void main(String[] args) {
		var ctx = SpringApplication.run(HeadlessFileSharingApplication.class, args);
		var terminalInterface = ctx.getBean(TerminalInterface.class);

		terminalInterface.start();
	}

}
