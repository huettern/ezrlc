% Reads touchstone file and plots information 
%
% EXAMPLE :
% IN.file = '../sample_files/bsp1.s1p';
% 
% 

% read Datafile
fid_log = fopen('SXPParse_log.txt','w');
[freq, data, freq_noise, data_noise, Zo] = SXPParse(IN.file, fid_log);


% Extract data from one port
parsed = zeros(length(data),1);
for k = 1 : length(data)
    parsed(k) = data(:,:,k);
end



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
%smithchart(parsed)



% This only works with RF toolbox
% data = read(rfdata.data,'../sample_files/bsp1.s1p');





