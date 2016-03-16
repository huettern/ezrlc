% Reads touchstone file and plots information 
%
% EXAMPLE :
% IN.file = '../sample_files/bsp1.s1p';
% 
% 
clear
clf
addpath incomming/s-param_toolbox/sbox/

IN.file = '../sample_files/r100l10uZRI.s1p';

% read Datafile
fid_log = fopen('SXPParse_log.txt','w');
[freq, data, freq_noise, data_noise, Zo] = SXPParse(IN.file, fid_log);


% Extract data from one port
data = s2z(data);
parsed = data(:);


% Plotting
subplot(221)
semilogx(freq,abs(parsed))
title('magnitude')
grid on

subplot(223)
semilogx(freq,angle(parsed))
title('phase')
grid on

subplot(222)
plot(freq,real(parsed))
grid on
%smithchart(parsed)



% This only works with RF toolbox
% data = read(rfdata.data,'../sample_files/bsp1.s1p');





