% function [w,yz,ys]=filereader

% Reads touchstone file and plots information 
%
% EXAMPLE :
% IN.file = '../sample_files/bsp1.s1p';
% 
% 
clear
addpath incomming/s-param_toolbox/sbox/

IN.file = '../sample_files/bsp6.s1p';

% read Datafile
fid_log = fopen('SXPParse_log.txt','w');
[freq, data, freq_noise, data_noise, Zo] = SXPParse(IN.file, fid_log);


% Extract data from one port
yz = s2z(Zo,data);
ys = yz;  %Umrechnen auf 50ohm R0
ys = z2s(50,ys);    
yz = yz(:);
ys = ys(:);
w=freq*2*pi;




% subplot(221)
% semilogx(freq,abs(parsed))
% title('magnitude')
% grid on
% 
% subplot(223)
% semilogx(freq,angle(parsed))
% title('phase')
% grid on
% 
% subplot(222)
% plot(freq,real(parsed))
% grid on
% hold on
%smithchart(parsed)
% end


% This only works with RF toolbox
% data = read(rfdata.data,'../sample_files/bsp1.s1p');





