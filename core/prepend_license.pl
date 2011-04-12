use strict;
use warnings;
use feature qw(switch say);
use File::Find;
use File::Path;

my $prepend_license = '/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
';

my $author = '\@author[\s]+[A-Za-z][A-Za-z0-9]+';

use Cwd;

my $dir = $ARGV[0];
if ($dir eq ''){
   $dir = getcwd;
}


say "RootDir = $dir";

# Modify Files
say "\n>>> START Modifying Files...";
find(\&prependlicense, $dir);
say "<<< END Modifying Files.";

sub prependlicense(){

   if (-d){
	say "\tDirectory: $_";
   }
   else {
	say "\t\tFile: $_";

	my $file = $_;

	if ($file =~ /.java$/){ 
		
		system("perl -pi.bak -e \"s/$author//g\" $_");

		&addheader($file,$prepend_license);

		say "\t\t\t... processed";
	}
   }
}


sub addheader{

	my($infile,$header)=@_;

	my $text = do { local( @ARGV, $/ ) = $infile ; <> } ;

	open FILE,">$infile" or die $!; 

	print FILE $header;

	print FILE $text;

	close FILE

}
