#!/usr/bin/octave -q
hold 'on'
ylim("manual");

while 1
			 data = load('data.txt');
			 ylim([0 max(data)]);
			 plot(data);
			 sleep(1)
end

