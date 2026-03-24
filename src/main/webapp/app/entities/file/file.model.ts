import { FileVisibility } from 'app/entities/enumerations/file-visibility.model';

export interface IFile {
  id: string;
  visibility?: keyof typeof FileVisibility | null;
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
