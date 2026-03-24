import { FileVisibility } from 'app/entities/enumerations/file-visibility.model';

export interface IFile {
  id: string;
<<<<<<< HEAD
  visibility?: FileVisibility | null;
=======
  visibility?: keyof typeof FileVisibility | null;
>>>>>>> b2c9e59 (Add public files support)
  url?: string | null;
  createdBy?: string | null;
  createdDate?: string | null;
  lastModifiedBy?: string | null;
  lastModifiedDate?: string | null;
}

// Cleaner NewFile type
export type NewFile = Omit<IFile, 'id' | 'createdBy' | 'createdDate' | 'lastModifiedBy' | 'lastModifiedDate'> & {
  id: null;
};
